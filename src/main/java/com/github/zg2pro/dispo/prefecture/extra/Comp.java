package com.github.zg2pro.dispo.prefecture.extra;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author zg2pro
 */
@Component
public class Comp {

    public boolean checkAvailibility() throws IOException {
        //curl "http://www.val-de-marne.gouv.fr/booking/create/4963/1" 
        //-H "Cookie: eZSESSID=9lp2b5rfmpn7eus77h094qu9b1; xtvrn=^$481980^$; xtan481980=-; xtant481980=1; cookies-accepte=oui" 
        //-H "Origin: http://www.val-de-marne.gouv.fr" 
        //-H "Accept-Encoding: gzip, deflate" 
        //-H "Accept-Language: fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7,vi;q=0.6" 
        //-H "Upgrade-Insecure-Requests: 1" 
        //-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36" 
        //-H "Content-Type: application/x-www-form-urlencoded" 
        //-H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8" 
        //-H "Cache-Control: max-age=0" 
        //-H "Referer: http://www.val-de-marne.gouv.fr/booking/create/4963/1" 
        //-H "Connection: keep-alive" 
        //--data "planning=5985^&nextButton=Etape+suivante" --compressed
        DefaultHttpClient httpclient = new DefaultHttpClient();
httpclient.getCredentialsProvider().setCredentials(
    new AuthScope("172.30.46.82", 8080),
    new UsernamePasswordCredentials("ganne", "M0m1m0m1*"));

//HttpHost targetHost = new HttpHost("TARGET HOST", 443, "https");
HttpHost proxy = new HttpHost("172.30.46.82", 8080);
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        HttpHost proxy = new HttpHost("172.30.46.82:8080");
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().setRoutePlanner(new DefaultProxyRoutePlanner(proxy) {
                    @Override
                    public HttpHost determineProxy(HttpHost target,
                            HttpRequest request, HttpContext context)
                                    throws HttpException {
                       // if (target.getHostName().equals("192.168.0.5")) {
                       //     return null;
                       // }
                        return super.determineProxy(target, request, context);
                    }

                }).build());
        
        //TODO: fix this at home
        
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        HttpHeaders headersPost = new HttpHeaders();
        headersPost.set("Cookie", "eZSESSID=9lp2b5rfmpn7eus77h094qu9b1; xtvrn=^$481980^$; xtan481980=-; xtant481980=1; cookies-accepte=oui");
        headersPost.set("Origin", "http://www.val-de-marne.gouv.fr");
        headersPost.set("Accept-Encoding", "gzip, deflate");
        headersPost.set("Accept-Language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7,vi;q=0.6");
        headersPost.set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headersPost.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        headersPost.set("Upgrade-Insecure-Requests", "1");
        headersPost.set("Content-Type", "application/x-www-form-urlencoded");
        headersPost.set("Cache-Control", "max-age=0");
        headersPost.set("Referer", "http://www.val-de-marne.gouv.fr/booking/create/4963/1");
        headersPost.set("Connection", "keep-alive");

        String[] guichets = new String[]{"5984", "5985", "5987"};
        int randomNum = ThreadLocalRandom.current().nextInt(0, 2 + 1);

        HttpEntity<String> entity = new HttpEntity<>("planning=" + guichets[randomNum] + "^&nextButton=Etape+suivante", headersPost);

        ResponseEntity<String> resPost = restTemplate.postForEntity("http://www.val-de-marne.gouv.fr/booking/create/4963/1", entity, String.class);

        //curl "http://www.val-de-marne.gouv.fr/booking/create/4963/2" 
        //-H "Accept-Encoding: gzip, deflate" 
        //-H "Accept-Language: fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7,vi;q=0.6" 
        //-H "Upgrade-Insecure-Requests: 1" 
        //-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36" 
        //-H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8" 
        //-H "Referer: http://www.val-de-marne.gouv.fr/booking/create/4963/1" 
        //-H "Cookie: eZSESSID=9lp2b5rfmpn7eus77h094qu9b1; xtvrn=^$481980^$; xtan481980=-; xtant481980=1; cookies-accepte=oui" 
        //-H "Connection: keep-alive" -H "If-Modified-Since: Sun, 11 Feb 2018 18:54:59 GMT" -H "Cache-Control: max-age=0" 
        //--compressed > test.html
        HttpHeaders headersGet = new HttpHeaders();
        headersGet.set("Accept-Encoding", "gzip, deflate");
        headersGet.set("Accept-Language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7,vi;q=0.6");
        headersGet.set("Upgrade-Insecure-Requests", "1");
        headersGet.set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headersGet.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        headersGet.set("Referer", "http://www.val-de-marne.gouv.fr/booking/create/4963/1");
        headersGet.set("Cookie", "eZSESSID=9lp2b5rfmpn7eus77h094qu9b1; xtvrn=^$481980^$; xtan481980=-; xtant481980=1; cookies-accepte=oui");
        headersGet.set("Connection", "keep-alive");

        ResponseEntity<String> resGet = restTemplate.exchange("http://www.val-de-marne.gouv.fr/booking/create/4963/2", HttpMethod.GET, entity, String.class);

        String body = resGet.getBody();

        boolean ret = !body.contains("plus de plage horaire");
        return ret;
    }

    private boolean sendMail() throws IOException {
        if (checkAvailibility()) {
            System.out.println("+++++++++++++");
            final NameValuePair[] data = {
                new BasicNameValuePair("phone", "+33652942131"),
                new BasicNameValuePair("message", "Hello world"),
                new BasicNameValuePair("key", "textbelt")
            };
            HttpClient httpClient = HttpClients.createMinimal();
            HttpPost httpPost = new HttpPost("https://textbelt.com/text");
            httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(data)));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            String responseString = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(responseString);
        } else {
            System.out.println("###############");
        }
        return true;
    }

    @Scheduled(fixedRate = 10000 * 60)
    public void sync() throws IOException {
        sendMail();
    }

}
