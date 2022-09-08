package com.cerf;

import com.cerf.cache.VendorCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class CacheInitializer {

    @Autowired
    VendorCache vendorCache;
    private final static Logger logger = LoggerFactory.getLogger(CacheInitializer.class);
    @PostConstruct
    private void init() {
        logger.info("Loading vendor cache data ...");
        vendorCache.createVendorCache();
    }
}
