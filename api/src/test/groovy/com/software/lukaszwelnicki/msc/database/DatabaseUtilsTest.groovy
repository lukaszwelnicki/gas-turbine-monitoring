package com.software.lukaszwelnicki.msc.database

import com.software.lukaszwelnicki.msc.config.YAMLConfig
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import spock.lang.Specification

class DatabaseUtilsTest extends Specification {

    def reactiveMongoTemplate = Mock(ReactiveMongoTemplate)
    def yamlConfig = Mock(YAMLConfig)
    def databaseUtils = new DatabaseUtils(reactiveMongoTemplate, yamlConfig)

    def "should prepare valid collection options object"() {
        given:
            1 * yamlConfig.getCappedCount() >> 10
            1 * yamlConfig.getCappedSize() >> 10
            def collectionOptions = databaseUtils.prepareCollectionOptionsForCapped()
        expect:
            collectionOptions.getMaxDocuments() == Optional.of(10L)
            collectionOptions.getSize() == Optional.of(10L)
            collectionOptions.getCapped() == Optional.of(true)
    }
}
