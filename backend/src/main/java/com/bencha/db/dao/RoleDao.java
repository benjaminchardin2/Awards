package com.bencha.db.dao;

import com.bencha.db.generated.QRole;
import com.bencha.db.generated.Role;
import com.coreoz.plume.db.querydsl.crud.CrudDaoQuerydsl;
import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RoleDao extends CrudDaoQuerydsl<Role> {
    @Inject
    public RoleDao(TransactionManagerQuerydsl transactionManagerQuerydsl) {
        super(transactionManagerQuerydsl, QRole.role);
    }

    public Long findRoleIdByLabel(String label) {
        return transactionManager
            .selectQuery()
            .select(QRole.role.id)
            .from(QRole.role)
            .where(QRole.role.label.eq(label))
            .fetchOne();
    }
}
