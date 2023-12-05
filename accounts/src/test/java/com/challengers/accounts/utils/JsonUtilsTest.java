package com.challengers.accounts.utils;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.gateways.rabbitmq.resources.TransactionMessage;
import com.challengers.accounts.support.TestSupport;
import com.challengers.accounts.templates.FixtureTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import java.time.LocalDate;

import static com.challengers.accounts.templates.FixtureTemplate.VALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class JsonUtilsTest  extends TestSupport {

//    private static final String CONVERTED_OBJECT = "converted object";
//    private static final String ID_UC_ORDER = "123";
//
//    @InjectMocks
//    private JsonUtils jsonUtils;
//
//    @Spy
//    private ObjectMapper mapper;
//
//    private Account orderId;
//
//    @BeforeEach
//    public void init() {
//        orderId = createObject(Account.class, VALID);
//        mapper.registerModule(new JavaTimeModule());
//    }
//
//    @Test
//    public void shouldConvertToJson() throws JsonProcessingException {
//        when(mapper.writeValueAsString(any())).thenReturn(CONVERTED_OBJECT);
//        final String convertedObject = jsonUtils.toJson(
//                new Account(orderId, ID_UC_ORDER));
//        assertEquals(convertedObject, CONVERTED_OBJECT);
//    }
//
//    @Test(expected = JsonUtilsException.class)
//    public void shouldThrowExceptionWhenObjectMapperFail() throws JsonProcessingException {
//        when(mapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
//        jsonUtils.toJson(new UncleChanOrderMessage(orderId, ID_UC_ORDER));
//    }
//
//    @Test
//    public void shouldConvertToObject() throws Exception {
//        final UncleChanOrderMessage uncleChanOrderMessage = new UncleChanOrderMessage(orderId,
//                ID_UC_ORDER);
//
//        final UncleChanOrderMessage convertedUncleChanOrderMessage = jsonUtils
//                .toObject(mapper.writeValueAsString(uncleChanOrderMessage), UncleChanOrderMessage.class);
//
//        assertThat(convertedUncleChanOrderMessage).usingRecursiveComparison()
//                .isEqualTo(uncleChanOrderMessage);
//    }
//
//    @Test(expected = JsonUtilsException.class)
//    public void shouldThrowExceptionWhenConvertToObjectFail() {
//        final String json = "json";
//        jsonUtils.toObject(json, UncleChanOrderMessage.class);
//    }
//
//    @Test
//    public void shouldConvertParametrizedClassToObject() throws Exception {
//        LocalDate now = LocalDate.now();
//
//        final Event<Object> expectedEvent = Event.builder()
//                .payload(CloseCycleMessage.builder()
//                        .cnpj("12345")
//                        .endDate(now)
//                        .build())
//                .build();
//
//        String json = mapper.writeValueAsString(expectedEvent);
//
//        final Event<CloseCycleMessage> event = jsonUtils
//                .toObject(json, Event.class, CloseCycleMessage.class);
//
//        assertThat(event).usingRecursiveComparison().isEqualTo(expectedEvent);
//    }
//
//    @Test(expected = JsonUtilsException.class)
//    public void shouldThrowExceptionWhenConvertParametrizedClassFail() {
//        jsonUtils.toObject("json", Event.class, String.class);
//    }
}
