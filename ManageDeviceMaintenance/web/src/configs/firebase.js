
import { initializeApp } from "firebase/app";
import { getDatabase, onValue, push, ref } from "firebase/database";

const firebaseConfig = {
  apiKey: "AIzaSyB6B4qwTyQIs-HOTmdn8kwPMAAle79J3y0",
  authDomain: "chats-fd917.firebaseapp.com",
  databaseURL: "https://chats-fd917-default-rtdb.firebaseio.com",
  projectId: "chats-fd917",
  storageBucket: "chats-fd917.appspot.com",
  messagingSenderId: "174728471714",
  appId: "1:174728471714:web:683ed1f602c309527923fb",
  measurementId: "G-P9F5EYVFJL"
};

const app = initializeApp(firebaseConfig);


export const db = getDatabase(app);

export const sendMessage = (sender, text) => {
  const messagesRef = ref(db, "chatroom/messages");
  return push(messagesRef, {
    sender,
    text,
    timestamp: Date.now()
  });
};

export const listenMessages = (callback) => {
  const messagesRef = ref(db, "chatroom/messages");
  onValue(messagesRef, (snapshot) => {
    const data = snapshot.val();
    const messages = data ? Object.values(data) : [];
    callback(messages.sort((a, b) => a.timestamp - b.timestamp));
  });
};
