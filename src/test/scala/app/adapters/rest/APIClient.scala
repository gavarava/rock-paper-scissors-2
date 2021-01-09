package app.adapters.rest

import java.net.URI

import org.springframework.http.{HttpEntity, HttpHeaders, MediaType}
import org.springframework.web.client.RestTemplate


object APIClient {

  def get(uri: URI): String = {
    val client = new RestTemplate
    client.getForObject(uri, classOf[String])
  }

  def post(uri: URI, inputData: Any): String = {
    val client = new RestTemplate
    val httpHeaders = new HttpHeaders
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    val responseEntity = client.postForEntity(uri, new HttpEntity(inputData, httpHeaders), classOf[String])
    responseEntity.getBody
  }

  def put(uri: URI, inputData: Any): Unit = {
    val client = new RestTemplate
    client.put(uri, inputData)
  }
}
