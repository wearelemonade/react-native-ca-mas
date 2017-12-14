import { NativeModules } from 'react-native';

const RNCaMAS = NativeModules.CaMAS;

const CaMAS = {};

CaMAS.debug = () => {
    return RNCaMAS.debug();
}

CaMAS.getState = () => {
    return RNCaMAS.getState();
}

CaMAS.isAuthenticationListenerRegistered = () => {
    return RNCaMAS.isAuthenticationListenerRegistered();
}

CaMAS.invoke = (path, options = {}) => {
    return RNCaMAS.invoke(path, options);
}

export default CaMAS;
