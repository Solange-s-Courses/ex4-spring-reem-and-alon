(() => {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const webSocket = (() =>{
        const socket = new SockJS("/chat-websocket");
        const stompClient = Stomp.over(socket);

        /* ============================ 1. sendMessage ============================ */
        const sendMessage = (content,chatId, userId) =>{
            console.log(`chat id is: ${chatId}`)
            console.log(`user id is: ${userId}`)
            stompClient.send("/app/chat", {}, JSON.stringify({
                chatId: chatId,
                senderId: userId,
                content: content
            }))
        };


        /* ========================= 2. connectToSocket ========================== */
        const connectToSocket = (chatId, userId) => {
            console.log(`chat id is: ${chatId}`)
            stompClient.connect({}, () => {
                console.log("STOMP connected");
                stompClient.subscribe(`/topic/messages`, (message) => {
                    const msg = JSON.parse(message.body);
                    if (msg.chatId === chatId) {
                        messageRenderer.render(msg, msg.senderId === userId);
                    }
                });
            });
        };
        return {
            sendMessage,
            connectToSocket
        }
    })()

    /* ========================== 3. messageRenderer ========================= */
    const messageRenderer = (() => {
        const messagesList = document.getElementById("messagesList");
        const startMsg = document.getElementById("startMsg");

        const formatDate = (isoString) => {
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
            console.log(dto, mine);

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

    /* ========================== DOM Ready =========================== */
    document.addEventListener("DOMContentLoaded", () => {
        const input = document.querySelector(".chatInput");
        const chatIdInput = document.querySelector('input[name="chatId"]');
        const userIdInput = document.querySelector('input[name="userId"]');
        const form = document.querySelector("form");

        if (chatIdInput){
            webSocket.connectToSocket(Number(chatIdInput.value), Number(userIdInput.value));
        }

        form?.addEventListener("submit", e =>{
            e.preventDefault()
            const text = input.value.trim();
            if (!text) return;
            webSocket.sendMessage(text, Number(chatIdInput.value), Number(userIdInput.value))
            input.value = "";
        });
    });
})();
