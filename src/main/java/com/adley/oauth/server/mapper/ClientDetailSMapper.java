package com.adley.oauth.server.mapper;

@Mapper
public interface ClientDetailsMapper {
  @Select(value="select client_id from oauth_client_details")
  List<String> findAllIds();
}
