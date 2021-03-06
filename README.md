## Quick Start


### @EnableRetrofitClients

```java
@EnableRetrofitClients
@EnableAutoConfiguration
public class RetrofitClientConfig {

}
```


### @RetrofitClient

```java
@RetrofitClient(name = "github", url = "${github.api.url}")
public interface Github {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @Data
    class Repo {

        String name;

        String full_name;
    }
}
```
### Test

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetrofitClientConfig.class)
public class SampleTest {

    @Autowired
    private Github github;

    @Test
    public void retrofitClientTest() throws IOException {
        assertNotNull(github);

        Call<List<Github.Repo>> call = github.listRepos("Square");

        List<Github.Repo> body = call.execute().body();

        body.forEach(System.out::println);
    }
}
```

## How to use

### configuration

使用 @RetrofitClient 的 configuration 属性指定配置类， 目前支持的bean如下

- Converter.Factory
- CallAdapter.Factory
- Call.Factory
- OkHttpClient.Builder

[e.g.](http://git.caimi-inc.com/baitouweng/retrofit-spring-boot/blob/master/retrofit-spring-boot-samples/src/main/java/com/wacai/retrofit/sample/client/Github.java)

### retrofit with hystrix

#### dependency

- maven 
```
<dependency>
    <groupId>com.netflix.hystrix</groupId>
    <artifactId>hystrix-core</artifactId>
    <version>x.y.z</version>
</dependency>
```

- gradle

```
  compile 'com.netflix.hystrix:hystrix-core:x.y.z'
```


#### application.proterties

```
retrofit.hystrix.enabled=true
```

#### retrofit interface

```java
@RetrofitClient(name = "github", url = "${github.api.url}")
public interface Github {

    @GET("users/{user}/repos")
    HystrixCommand<List<Repo>> returnHystrixCommandBody(@Path("user") String user);

    @Data
    class Repo {

        String name;

        String full_name;
    }
}
```
there can return HystrixCommand Observable Single Completable Response and ResponseBody

- commandGroup

```
github // ${RetrofitClient.name} 

```

- commandKey

```
GET#users/{user}/repos // ${HttpMethod#relativeUrl}
```


