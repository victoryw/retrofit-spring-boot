package cn.dysania.retrofit.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * TODO 类描述
 *
 * @author baitouweng
 */

@Configuration
public class RetrofitClientsConfiguration {

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public Retrofit.Builder retrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient.Builder okHttpClient(){
        return new OkHttpClient.Builder();
    }

    @Configuration
    @ConditionalOnClass(GsonConverterFactory.class)
    protected static class CoverterConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public Converter.Factory converterFactory() {
            return GsonConverterFactory.create();
        }
    }
}