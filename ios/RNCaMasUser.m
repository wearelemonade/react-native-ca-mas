#import "RNCaMasUser.h"
#import "NSObject+NSDictionary.h"

#import <MASFoundation/MASFoundation.h>
#import <MASConnecta/MASConnecta.h>
#import <MASIdentityManagement/MASIdentityManagement.h>
#import <MASStorage/MASStorage.h>

@implementation RNCaMasUser

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE(CaMASUser)

NSString *E_LOGIN_ERROR = @"E_LOGIN_ERROR";
NSString *E_LOGOUT_ERROR = @"E_LOGOUT_ERROR";

RCT_EXPORT_METHOD(getAuthCredentialsType
    :(RCTPromiseResolveBlock)resolve
    rejecter:(RCTPromiseRejectBlock)reject
) {
    resolve([MASUser authCredentialsType]);
}

RCT_EXPORT_METHOD(getCurrentUser
    :(RCTPromiseResolveBlock)resolve
    rejecter:(RCTPromiseRejectBlock)reject
) {
    resolve([[MASUser currentUser] getDictionary]);
}

RCT_EXPORT_METHOD(login
    :(NSString *)username
    password:(NSString *)password
    resolver:(RCTPromiseResolveBlock)resolve
    rejecter:(RCTPromiseRejectBlock)reject
) {
    [MASUser loginWithUserName:username password:password completion:^(BOOL completed, NSError *error) {
        if (error)
        {
            reject(E_LOGIN_ERROR, error.localizedDescription, error);
        }
        else {
            resolve(@(completed));
        }
    }];
}

RCT_EXPORT_METHOD(logout
      :(RCTPromiseResolveBlock)resolve
      rejecter:(RCTPromiseRejectBlock)reject
) {
    [[MASUser currentUser] logoutWithCompletion:^(BOOL completed, NSError * _Nullable error) {
        if (error) {
            reject(E_LOGOUT_ERROR, error.localizedDescription, error);
        } else {
            resolve(@(completed));
        }
    }];
}

@end
