(() => {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const form = document.querySelector("form.chat-input");
    // ============ ניהול מונה הודעות שלא נקראו ============
    const renderUnreadBadge=(chatId) =>{
        const chatItem = document.querySelector(`[data-chat-id="${chatId}"] .unread-badge`);
        const count = unreadCounts[chatId] || 0;
        if (chatItem) {
            if (count > 0) {
                chatItem.textContent = count;
                chatItem.style.display = 'inline-block';
            } else {
                chatItem.textContent = '';
                chatItem.style.display = 'none';
            }
        }
    }

    // =============== WebSocket ניהול =================
    const webSocket = (() => {
        const socket = new SockJS("/chat-websocket");
        const stompClient = Stomp.over(socket);

        const sendMessage = (content, chatId, userId) => {
            stompClient.send("/app/chat", {}, JSON.stringify({
                chatId: chatId,
                senderId: userId,
                content: content
            }));
        };

        const connectToSocket = (chatId, userId) => {
            stompClient.connect({}, () => {
                stompClient.subscribe(`/topic/messages`, (message) => {
                    const msg = JSON.parse(message.body);

                    if (chatId && msg.chatId === chatId) {
                        messageRenderer.render(msg, msg.senderId === userId);
                        if (msg.senderId !== userId){
                            fetch(`/api/chat/${msg.messageId}/read`, {
                                method: "PUT",
                            }).catch(err => {
                                console.log(err.response.data)
                            })
                        }
                        unreadCounts[msg.chatId] = 0;
                        renderUnreadBadge(msg.chatId);
                    } else {
                        if (msg.senderId !== userId) {
                            unreadCounts[msg.chatId] = (unreadCounts[msg.chatId] || 0) + 1;
                            renderUnreadBadge(msg.chatId);
                        }
                    }
                });

                stompClient.subscribe("/user/queue/errors", (message) => {
                    console.log(message.body)
                    const msg = JSON.parse(message.body);
                    console.log(msg.body)
                    if (msg.body === "SESSION_EXPIRED") {
                        window.location.href = "/login?expired";
                    } else {
                        alert("שגיאה: " + msg.body);
                    }
                });

            });
        };

        const disconnectSocket = () => {
            if (stompClient && stompClient.connected) {
                stompClient.disconnect();
            }
        };

        return {
            sendMessage,
            connectToSocket,
            disconnectSocket
        };
    })();

    // =============== Renderer ===============
    const messageRenderer = (() => {
        const messagesList = document.getElementById("messagesList");
        const startMsg = document.getElementById("startMsg");

        const formatDate = (isoString) => {
            if (!isoString) return "---";
            const date = new Date(isoString);
            return date.toLocaleString('he-IL', {
                hour: '2-digit',
                minute: '2-digit',
                day: '2-digit',
                month: '2-digit',
                year: 'numeric'
            });
        };

        const render = (dto, mine) => {
            const wrap = document.createElement("div");
            wrap.className = `message ${mine ? "sent" : "received"}`;
            wrap.innerHTML = `
                <div>${dto.content}</div>
                <div class="message-time">
                  ${formatDate(dto.sentAt)}
                </div>`;
            if (startMsg) startMsg.remove();
            messagesList.appendChild(wrap);
            messagesList.scrollTop = messagesList.scrollHeight;
        };

        return { render };
    })();

    // ============== DOM Ready ==============
    document.addEventListener("DOMContentLoaded", () => {
        const input = document.querySelector(".chatInput");
        const chatIdInput = document.querySelector('input[name="chatId"]');
        const userIdInput = document.querySelector('input[name="userId"]');
        const chatLinks = document.querySelectorAll('[data-chat-id]');
        chatLinks.forEach(link => {
            const chatId = link.getAttribute('data-chat-id');
            renderUnreadBadge(chatId);
        });

        let currentChatId = chatIdInput ? Number(chatIdInput.value) : null;
        let currentUserId = userIdInput ? Number(userIdInput.value) : null;

        webSocket.connectToSocket(currentChatId, currentUserId);
        form?.addEventListener("submit", e => {
            e.preventDefault();
            const text = input.value.trim();
            if (!text) return;
            webSocket.sendMessage(text, currentChatId, currentUserId);
            input.value = "";
        });
    });
})();