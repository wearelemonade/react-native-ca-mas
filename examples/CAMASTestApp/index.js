import { AppRegistry } from 'react-native';
import App from './App';
import { MAS, MASUser } from 'react-native-ca-mas';

console.log('MAS functions', MAS)
console.log('MASUserfunctions', MASUser)

MAS.debug()

MASUser.login('USERNAME', 'PASSWORD')
.then(loginOk => {
    MAS.invoke('/MY_ENDPOINT', {
        method: 'POST',
        body: {},
        header: {'content-type': 'application/json'},
    })
    .then(res => console.log('response', res))
    .catch(error => console.log('error', error))
})
.catch(error => console.log('login error:', error))

AppRegistry.registerComponent('CAMASTestApp', () => App);
