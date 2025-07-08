(() => {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const chatId = Number(document.querySelector('input[name="chatId"]').value);
    const userId = Number(document.querySelector('input[name="userId"]').value);
    const form = document.querySelector("form");
    const input = form.querySelector(".chatInput");

    /* ============================ 1. sendMessage ============================ */
    const sendMessage = (chatId, content) =>
        fetch("/api/chat/send", {
            method: "POST",
            headers: {
                [csrfHeader]: csrfToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                chatId: chatId,
                senderId: userId,
                content: content,
                sentAt: new Date().toISOString()})
        }).then(res => {
            if (!res.ok) throw new Error("Failed to send");
            return res;
        });

    /* ========================= 2. connectToSocket ========================== */
    const connectToSocket = (chatId, userId) => {
        // חייבים להטעין sockjs-client ו stomp.js בסקריפט חיצוני בדף
        const socket = new SockJS("/chat-websocket");
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, () => {
            console.log("STOMP connected");

            stompClient.subscribe(`/topic/conversation/${chatId}`, (message) => {
                try {
                    const messageDTO = JSON.parse(message.body);
                    if (messageDTO.chatId === chatId) {

                        messageRenderer.render(messageDTO, messageDTO.senderId === userId);
                    }
                } catch (e) {
                    console.error("Invalid STOMP message", e);
                }
            });
        });
    };

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

    /* ============================ 4. formHandler =========================== */
    const formHandler = (() => {

        const onSubmit = ev => {
            ev.preventDefault();
            const text = input.value.trim();
            if (!text) return;

            sendMessage(chatId, text)
                .then(() => {
                    input.value = "";
                })
                .catch(console.error);
        };

        return { onSubmit };
    })();

    /* ========================== DOM Ready =========================== */
    document.addEventListener("DOMContentLoaded", () => {
        form.addEventListener("submit", formHandler.onSubmit);
        connectToSocket(chatId, userId);
    });

})();
