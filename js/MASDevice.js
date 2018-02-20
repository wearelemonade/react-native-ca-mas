import { NativeModules } from 'react-native';

const RNCaMASDevice = NativeModules.CaMASDevice;

const CaMASDevice = {};

CaMASDevice.deregister = () => {
    return RNCaMASDevice.deregister();
}

CaMASDevice.getIdentifier = () => {
    return RNCaMASDevice.getIdentifier();
}

CaMASDevice.isRegistered = () => {
    return RNCaMASDevice.isRegistered();
}

CaMASDevice.resetLocally = () => {
    return RNCaMASDevice.resetLocally();
}

export default CaMASDevice;
