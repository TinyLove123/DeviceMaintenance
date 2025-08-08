
import { initializeApp } from "firebase/app";
import { getDatabase, onValue, push, ref } from "firebase/database";

const firebaseConfig = {
    apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
    authDomain: process.env.REACT_APP_FIREBASE_AUTH_DOMAIN,
    databaseURL: process.env.REACT_APP_FIREBASE_DATABASE_URL,
    projectId: process.env.REACT_APP_FIREBASE_PROJECT_ID,
    storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
    messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
    appId: process.env.REACT_APP_FIREBASE_APP_ID,
    measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID
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
