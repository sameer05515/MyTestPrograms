# React JS

# Section 1

- **Documentation**- https://legacy.reactjs.org/
- **Reactive** -> things happens instantly
- 





















# Remembrable
## Different hooks in ReactJS
- **useState**
  - The useState hook allows us to create state variables in a React function component.
- **useEffect**
  - useEffect lets us perform side effects in function components.
- **useContext**
  0 useContext gives us a simple function to access the data we provided on the value prop of the Context Provider in any child component.
- **useReducer**
  - useReducer is a hook for state management, much like useState, and relies upon a kind of function called a reducer.
  - useReducer can be used in many of the same ways that useState can, but is more helpful for managing state across multiple components that may involve different operations or "actions".
- **useCallback**
  - useCallback is a hook that is used for improving our component performance.
- **useMemo**
  - useMemo is very similar to useCallback and helps improve performance. But instead of being for callbacks, it is for storing the results of expensive operations.
- **useRef**
  - useRef allows us to easily use React refs. They are helpful when we want to directly interact with an element, such as to clear its value or focus it, as with an input.
- **useImperativeHandle**
- **useLayoutEffect**
- **useDebugValue**
- **useDeferredValue**
- **useTransition**
- **useId**
- **useSyncExternalStore**
- **useInsertionEffect**


# My queries
- Why 2 approaches functional and class based approach
- Why use redux
- why js and jsx both extentions used in react
- What are different hooks?

## Some common issues and its workaround

### Issue 1: 
```
Error: error:0308010C:digital envelope routines::unsupported
    at new Hash (node:internal/crypto/hash:67:19)
    at Object.createHash (node:crypto:130:10)
    at module.exports (/Users/user/Programming Documents/WebServer/untitled/node_modules/webpack/lib/util/createHash.js:135:53)
    at NormalModule._initBuildHash (/Users/user/Programming Documents/WebServer/untitled/node_modules/webpack/lib/NormalModule.js:417:16)
    at handleParseError (/Users/user/Programming Documents/WebServer/untitled/node_modules/webpack/lib/NormalModule.js:471:10)
    at /Users/user/Programming Documents/WebServer/untitled/node_modules/webpack/lib/NormalModule.js:503:5
    at /Users/user/Programming Documents/WebServer/untitled/node_modules/webpack/lib/NormalModule.js:358:12
    at /Users/user/Programming Documents/WebServer/untitled/node_modules/loader-runner/lib/LoaderRunner.js:373:3
    at iterateNormalLoaders (/Users/user/Programming Documents/WebServer/untitled/node_modules/loader-runner/lib/LoaderRunner.js:214:10)
    at iterateNormalLoaders (/Users/user/Programming Documents/WebServer/untitled/node_modules/loader-runner/lib/LoaderRunner.js:221:10)
/Users/user/Programming Documents/WebServer/untitled/node_modules/react-scripts/scripts/start.js:19
  throw err;
```
- You can try one of these:
1. Downgrade to Node.js v16.  
You can reinstall the current LTS version from Node.js’ website.  
You can also use nvm. For Windows, use nvm-windows.
2. Enable legacy OpenSSL provider.  
On Unix-like (Linux, macOS, Git bash, etc.):  
```export NODE_OPTIONS=--openssl-legacy-provider```  
On Windows command prompt:  
```set NODE_OPTIONS=--openssl-legacy-provider```  
On PowerShell:  
```$env:NODE_OPTIONS = "--openssl-legacy-provider"```  
