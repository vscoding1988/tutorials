# State is null in event listener

Let's assume we have a simple component. It has a state `user` and attaches events in `useEffect`.
One creates a user and another uses it. 

```javascript
import {useEffect, useState} from "react";

function App() {
  const [user, setUser] = useState(readUserFromLocalStorage);

  useEffect(() => {
    document.addEventListener('onClick', onClickEvent);
    document.addEventListener('onUserCreation', onUserCreation);
  }, [])

  const onClickEvent = (e) => {
    console.log(user);
  }

  const onUserCreation = ({detail}) => {
    localStorage.setItem("user", JSON.stringify(newUser));
    setUser(detail);
  }
  
  function readUserFromLocalStorage() {
    let oldUserStr = localStorage.getItem("user");

    if (oldUserStr) {
      return JSON.parse(oldUserStr);
    } 
    
    return null;
  }
}
```

So basically we have the following use cases:

- There is initial user in the `localStorage` and `onClickEvent` is executed
  - We would expect it to log the `user`
- There is no initial user in the `localStorage` and `onClickEvent` is executed
  - We would expect it to log the `null`
- There is no initial user in the `localStorage` and `onUserCreation` is executed and after it `onClickEvent`
  - We would expect it to log the `user`, **but when we try this, it logs `null`**

## Solution

It looks like, the callback is using the value of the state it had, when the callback was defined. So 
we need to re-attach them after each state change:

```javascript
function App() {
  const [user, setUser] = useState(readUserFromLocalStorage);

  useEffect(() => {
    document.addEventListener('onClick', onClickEvent);
    document.addEventListener('onUserCreation', onUserCreation);
    
    // remove callbacks before re-attach them
    return () => {
      document.removeEventListener('onClick', onClickEvent);
      document.removeEventListener('onUserCreation', onUserCreation);
    }
  }, [user]) // useEffect will be executed, when the user changes. 
}
```
