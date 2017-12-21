#import "RNCaMas.h"
#import <React/RCTLog.h>

#import <MASFoundation/MASFoundation.h>
#import <MASConnecta/MASConnecta.h>
#import <MASIdentityManagement/MASIdentityManagement.h>
#import <MASStorage/MASStorage.h>

@implementation RNCaMas

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE(CaMAS)

NSString *E_JSON_PARSE_ERROR = @"E_JSON_PARSE_ERROR";
NSString *E_REQUEST_ERROR = @"E_REQUEST_ERROR";

+ (NSDictionary*) treatResponse:(NSDictionary *)responseInfo {
    NSMutableDictionary *resTreated = [[NSMutableDictionary alloc] init];
    if ([responseInfo objectForKey:@"MASResponseInfoBodyInfoKey"]) {
        resTreated[@"body"] = [responseInfo objectForKey:@"MASResponseInfoBodyInfoKey"];
    }
    if ([responseInfo objectForKey:@"MASResponseInfoHeaderInfoKey"]) {
        resTreated[@"headers"] = [responseInfo objectForKey:@"MASResponseInfoHeaderInfoKey"];
    }
    return resTreated;
}

RCT_EXPORT_METHOD(debug)
{
    [MAS setGatewayNetworkActivityLogging:true];
}

RCT_EXPORT_METHOD(getState:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    MASState currentState = [MAS MASState];
    resolve(@(currentState));
}

RCT_EXPORT_METHOD(isAuthenticationListenerRegistered:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    // TODO: to implement
    reject(@"NOT_IMPLEMENTED", @"isAuthenticationListenerRegistered not implemented", nil);
}

RCT_EXPORT_METHOD(invoke:(NSString *)path options:(NSDictionary *)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    NSString *method = @"";
    if ([options objectForKey:@"method"]) {
        method = [options objectForKey:@"method"];
    }

    method = [method uppercaseString];
    if ([method isEqualToString:@"GET"] || [method isEqualToString:@""]) {
        [MAS getFrom
            :path
            withParameters:nil
            andHeaders:[options objectForKey:@"headers"]
            completion:^(NSDictionary *responseInfo, NSError *error
        ) {
            if (error) {
                reject(E_REQUEST_ERROR, error.localizedDescription, error);
            } else {
                resolve([RNCaMas treatResponse:responseInfo]);
            }
        }];
    } else if ([method isEqualToString:@"POST"]) {
        [MAS postTo
            :path
            withParameters:[options objectForKey:@"body"]
            andHeaders:[options objectForKey:@"headers"]
            completion:^(NSDictionary *responseInfo, NSError *error
        ) {
            if (error) {
                reject(E_REQUEST_ERROR, error.localizedDescription, error);
            } else {
                resolve([RNCaMas treatResponse:responseInfo]);
            }
        }];
    } else if ([method isEqualToString:@"PUT"]) {
        [MAS putTo
            :path
            withParameters:[options objectForKey:@"body"]
            andHeaders:[options objectForKey:@"headers"]
            completion:^(NSDictionary *responseInfo, NSError *error
        ) {
            if (error) {
                reject(E_REQUEST_ERROR, error.localizedDescription, error);
            } else {
                resolve([RNCaMas treatResponse:responseInfo]);
            }
        }];
    } else if ([method isEqualToString:@"DELETE"]) {
        [MAS deleteFrom
            :path
            withParameters:nil
            andHeaders:[options objectForKey:@"headers"]
            completion:^(NSDictionary *responseInfo, NSError *error
        ) {
            if (error) {
                reject(E_REQUEST_ERROR, error.localizedDescription, error);
            } else {
                resolve([RNCaMas treatResponse:responseInfo]);
            }
        }];
    } else {
        reject(E_REQUEST_ERROR, [NSString stringWithFormat: @"Request method %@ not recognized", method], nil);
    }
}

@end
