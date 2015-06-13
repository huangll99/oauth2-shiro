/*
 * Copyright (c) 2013 Andaily Information Technology Co. Ltd
 * www.andaily.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Andaily Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with Andaily Information Technology Co. Ltd.
 */
package com.monkeyk.os.infrastructure.jdbc;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthRepository;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
@Repository("oauthJdbcRepository")
public class OauthJdbcRepository extends AbstractJdbcRepository implements OauthRepository {


    private static ClientDetailsRowMapper clientDetailsRowMapper = new ClientDetailsRowMapper();


    @Override
    public ClientDetails findClientDetails(String clientId) {
        final String sql = " select * from oauth_client_details where archived = 0 and client_id = ? ";
        final List<ClientDetails> list = jdbcTemplate.query(sql, clientDetailsRowMapper, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int saveClientDetails(final ClientDetails clientDetails) {
        final String sql = " insert into oauth_client_details(client_id,client_secret) values (?,?)";

        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, clientDetails.getClientId());
                ps.setString(2, clientDetails.getClientSecret());
                //more setter
            }
        });
    }
}
