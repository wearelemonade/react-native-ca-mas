declare module 'react-native-ca-mas' {
    export interface IMAS {
        debug: () => void
        getState: () => PromiseLike<number>
        invoke: <B, O>(path: string, options: IInvokeOptions<O>) => Promise<IInvokeResponse<B>>
        isAuthenticationListenerRegistered: () => PromiseLike<boolean>
    }

    export interface IMASDevice {
        deregister: () => Promise<void>
        getIdentifier: () => PromiseLike<string>
        isRegistered: () => PromiseLike<boolean>
        resetLocally: () => void
    }

    export interface IMASUser {
        getAuthCredentialsType: () => PromiseLike<string>
        getCurrentUser: () => PromiseLike<any>
        login: (username: string, password: string) => Promise<boolean>
        logout: () => Promise<boolean>
    }

    export interface IInvokeOptions<B = any> {
        body?: B
        headers?: object
        method: 'DELETE' | 'GET' | 'POST' | 'PUT'
    }

    export interface IInvokeResponse<B = any> {
        body?: B
        headers?: object
        message?: string
        statusCode?: number
    }

    export var MAS: IMAS
    export var MASDevice: IMASDevice
    export var MASUser: IMASUser
}
