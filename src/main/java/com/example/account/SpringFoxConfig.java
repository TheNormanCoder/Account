package com.example.account;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringFoxConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    Schema currencySchema = new StringSchema()
            .description("The currency of the money transfer. " +
                    "Please note that this is the currency that will be used to credit the receiver with the specified amount of money. " +
                    "If the account on which the money transfer is executed has a different currency, " +
                    "additional currency conversion fees may apply.")
            .pattern("^[A-Z]{3}$");

    //fiscalCode

    StringSchema fiscalCode = (StringSchema) new StringSchema()
                    .description("The Italian Partita IVA of the legal person.")
                    .pattern("^[A-Z0-9]{16}$");
    Schema legalPersonBeneficiarySchema =new ObjectSchema()
            .addProperty("fiscalCode", fiscalCode)
            .required(List.of("fiscalCode"));

    StringSchema fiscalCode1Schema = (StringSchema) new StringSchema()
            .description("The Italian Codice Fiscale of the first beneficiary. The subsequent fields (2 to 5) are optional.")
            .pattern("^[A-Z0-9]{16}$");
    Schema naturalPersonBeneficiarySchema = new ObjectSchema()
            .addProperty("fiscalCode1", fiscalCode1Schema)
            .required(List.of("fiscalCode1"));

    Schema taxReliefSchema = new ObjectSchema()
            .addProperty("isCondoUpgrade", new BooleanSchema()
                    .description("Flag to indicate if the tax relief is related to upgrades of common condominium spaces."))
            .addProperty("creditorFiscalCode", new StringSchema()
                    .description("The fiscal code (either the Italian Codice Fiscale or Partita IVA) of the money transfer creditor.")
                    .pattern("^([A-Z0-9]{11}|[0-9]{11})$")
            )
            .addProperty("beneficiaryType", new StringSchema()
                    .description("The type of the tax relief beneficiary.")
                    ._enum(List.of("NATURAL_PERSON", "LEGAL_PERSON"))
            )
            .addProperty("naturalPersonBeneficiary",naturalPersonBeneficiarySchema)
            .addProperty("legalPersonBeneficiary",legalPersonBeneficiarySchema)
            .required(List.of("isCondoUpgrade","creditorFiscalCode","beneficiaryType"));

    Schema accountSchema = new Schema()
            .addProperty("accountCode", new StringSchema()
                    .example("IT60X0542811101000000123456")
                    .pattern("[A-Z]{2}\\d{2}[A-Z]{4}\\d{10}")
                    .description("The account code in IBAN format"))
            .required(List.of("accountCode"));

    Schema account = new Schema()
            .addProperty("currency", accountSchema)
            .required(List.of("currency"));

    Schema name = new StringSchema()
            .example("John Doe")
            .maxLength(70)
            .description("The name of the payment")
            .pattern("[A-Za-z\\s]*");
    Schema creditor = new ObjectSchema()
            .addProperty("name",name)
            .addProperty("account",account)
    .required(List.of("name","account"));

    Schema paymentSchema = new Schema()
            .addProperty("creditor",creditor)
            .addProperty("description", new StringSchema()
                    .example("fees")
                    .maxLength(140)
                    .description("The name of the payment")
                    .pattern("[A-Za-z\\s]*"))
            .addProperty("amount", new NumberSchema()
                    .description("The amount of the payment")
                    .format("double"))
            .addProperty("currency", currencySchema)
            .addProperty("taxRelief", taxReliefSchema)
    .required(List.of("creditor","description","amount","currency"));

    return new OpenAPI().info(new Info().title("API Documentation").version("1.0.0"))
            .components(new Components().addSchemas("Payment", paymentSchema));
  }



  @Bean
  public GroupedOpenApi customGroupedOpenApi() {
    return GroupedOpenApi.builder()
        .group("api")
        .pathsToMatch("/api/**")
        .build();
  }
}
