package com.bencha.services;

import com.bencha.db.dao.LegalDao;
import com.bencha.db.generated.Legal;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class LegalService {
    private final LegalDao legalDao;

    @Inject
    public LegalService(LegalDao legalDao) {
        this.legalDao = legalDao;
    }

    public Map<String, String> getLegalPages() {
        return legalDao
            .findAll()
            .stream()
            .collect(Collectors.toMap(
                Legal::getPage,
                Legal::getContent
            ));
    }
}
