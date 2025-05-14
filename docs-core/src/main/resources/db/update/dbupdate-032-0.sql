create table T_REGISTRATION_REQUEST (
    REQ_ID_C varchar(36) not null,
    REQ_USERNAME_C varchar(50) not null,
    REQ_EMAIL_C varchar(100) not null,
    REQ_PASSWORD_C varchar(100) not null,
    REQ_STATUS_C varchar(20) not null,
    REQ_CREATEDATE_D datetime not null,
    REQ_RESPONSEDATE_D datetime,
    REQ_RESPONSEMSG_C varchar(500),
    primary key (REQ_ID_C)
);

insert into T_ROLE(ROL_ID_C, ROL_NAME_C, ROL_CREATEDATE_D) values('pending', 'Pending', NOW());
update T_CONFIG set CFG_VALUE_C = '32' where CFG_ID_C = 'DB_VERSION'; 

-- // AI-Generation: by Cursor
-- // prompt：New user request: a guest could send a request to admin to register as a new user. The
-- // admin could accept or reject the request. Once accepted, the guest could use the new account to login 