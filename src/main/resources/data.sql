inser into oauth_client_details
(client_id,client_secret,scope,authorized_grant_types,web_server_redirect_uri,authorities,acess_token_validity,
refresh_token_validity,additional_information,autoapprove)
values
('blogClientId','bCriptPassword(secret)','foo,read,write',
'password,authorization_code,refresh_token,client_credentials','http://www.oauth2-client.com:8089/granttoken','ROLE_REGISTERED_CLIENT',
36000,36000,null,true);

inser into oauth_client_details
(client_id,client_secret,scope,authorized_grant_types,web_server_redirect_uri,authorities,acess_token_validity,
refresh_token_validity,additional_information,autoapprove)
values
('resourceClientId','bCriptPassword(secret)','foo,read,write',
'authorization_code',null,'ROLE_TRUSTED_CLIENT',
36000,36000,null,true);
