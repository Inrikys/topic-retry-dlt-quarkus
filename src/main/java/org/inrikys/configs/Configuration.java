package org.inrikys.configs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.inrikys.domain.ports.*;
import org.inrikys.domain.services.*;

public class Configuration {

    @Produces
    @ApplicationScoped
    public CreateNewUser createNewUser(CreateNewUserPort createNewUserPort) {
        return new CreateNewUser(createNewUserPort);
    }

    @Produces
    @ApplicationScoped
    public CreateNewProduct createNewProduct(CreateNewProductPort createNewProductPort) {
        return new CreateNewProduct(createNewProductPort);
    }

    @Produces
    @ApplicationScoped
    public GetProducts getProducts(GetProductsPort getProductsPort) {
        return new GetProducts(getProductsPort);
    }

    @Produces
    @ApplicationScoped
    public GetUsers getUsers(GetUsersPort getUsersPort) {
        return new GetUsers(getUsersPort);
    }

    @Produces
    @ApplicationScoped
    public CreateNewReview createNewReview(GetUsersPort getUsersPort, GetProductsPort getProductsPort, CreateNewReviewPort createNewReviewPort) {
        return new CreateNewReview(getUsersPort, getProductsPort, createNewReviewPort);
    }

}
