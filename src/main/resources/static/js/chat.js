(() => {
    // ============ ניהול מונה הודעות שלא נקראו ============
    function renderUnreadBadge(chatId) {
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

    function resetUnread(chatId) {
        unreadCounts[chatId] = 0;
        renderUnreadBadge(chatId);
    }

    // =============== WebSocket ניהול =================
    const webSocket = (() => {
        const socket = new SockJS("/chat-websocket");
        const stompClient = Stomp.over(socket);

        let isConnected = false;

        const sendMessage = (content, chatId, userId) => {
            if (!isConnected) {
                console.warn("WebSocket not connected yet");
                return;
            }
            stompClient.send("/app/chat", {}, JSON.stringify({
                chatId: chatId,
                senderId: userId,
                content: content
            }));
        };

        const connectToSocket = (chatId, userId, onConnected) => {
            stompClient.connect({}, () => {
                isConnected = true;

                stompClient.subscribe(`/topic/messages`, (message) => {
                    const msg = JSON.parse(message.body);

                    if (msg.chatId === chatId) {
                        messageRenderer.render(msg, msg.senderId === userId);
                    } else {
                        if (msg.senderId !== userId) {
                            unreadCounts[msg.chatId] = (unreadCounts[msg.chatId] || 0) + 1;
                            renderUnreadBadge(msg.chatId);
                        }
                    }
                });

                if (typeof onConnected === "function") {
                    onConnected();
                }
            });
        };

        return {
            sendMessage,
            connectToSocket
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
        const form = document.querySelector("form");

        let currentChatId = chatIdInput ? Number(chatIdInput.value) : null;
        let currentUserId = userIdInput ? Number(userIdInput.value) : null;

        if (chatIdInput){
            webSocket.connectToSocket(currentChatId, currentUserId);
            resetUnread(currentChatId);
        }

        form?.addEventListener("submit", e =>{
            e.preventDefault()
            const text = input.value.trim();
            if (!text) return;
            webSocket.sendMessage(text, currentChatId, currentUserId)
            input.value = "";
        });
    });
})();
