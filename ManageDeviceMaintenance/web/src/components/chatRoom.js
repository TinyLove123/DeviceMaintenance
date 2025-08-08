import { onValue, push, ref, set, update } from "firebase/database";
import { useContext, useEffect, useState } from "react";
import { MyUserContext } from "../configs/Context";
import { db } from "../configs/firebase";

const ChatRoom = () => {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");
    const [user] = useContext(MyUserContext);

    useEffect(() => {
        if (!user) return;

        const userRef = ref(db, `chatroom/participants/${user.id}`);
        update(userRef, { name: user.username, joinedAt: Date.now() });

        const messagesRef = ref(db, "chatroom/messages");
        const unsubscribe = onValue(messagesRef, (snapshot) => {
            const data = snapshot.val();
            const msgList = data
                ? Object.entries(data).map(([id, msg]) => ({ id, ...msg }))
                : [];
            setMessages(msgList.sort((a, b) => a.timestamp - b.timestamp));
        });

        return () => unsubscribe();
    }, [user]);

    const sendMessage = () => {
        if (!user || !input.trim()) return;

        const messagesRef = ref(db, "chatroom/messages");
        const newMsgRef = push(messagesRef);
        set(newMsgRef, {
            userId: user.id,
            username: user.username,
            text: input,
            timestamp: Date.now()
        });
        setInput("");
    };

    return (
        <div style={{ maxWidth: 600, margin: "auto" }}>
            <h2> Phòng Chat Chung</h2>
            <div
                style={{
                    border: "1px solid #ccc",
                    padding: "10px",
                    height: "400px",
                    overflowY: "auto",
                    marginBottom: "10px"
                }}
            >
                {messages.map((msg) => (
                    <p key={msg.id} style={{ color: msg.userId === user.id ? "blue" : "black" }}>
                        <strong>{msg.username}:</strong> {msg.text}
                    </p>
                ))}
            </div>
            <input
                type="text"
                value={input}
                onChange={(e) => setInput(e.target.value)}
                placeholder="Nhập tin nhắn..."
                style={{ width: "80%", padding: "8px" }}
            />
            <button onClick={sendMessage} style={{ padding: "8px 12px" }}>
                Gửi
            </button>
        </div>
    );
};

export default ChatRoom;
