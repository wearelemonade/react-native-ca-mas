import { NativeModules } from 'react-native';

const RNCaMASUser = NativeModules.CaMASUser;

const CaMASUser = {};

CaMASUser.getAuthCredentialsType = () => {
    return RNCaMASUser.getAuthCredentialsType();
}

CaMASUser.getCurrentUser = () => {
    return RNCaMASUser.getCurrentUser();
}

CaMASUser.login = (username, password) => {
    return RNCaMASUser.login(username, password);
}

CaMASUser.logout = () => {
    return RNCaMASUser.logout();
}

export default CaMASUser;
