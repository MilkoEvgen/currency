create table rate_providers
(
    provider_code      varchar(3)              not null        primary key,
    created_at         timestamp default now() not null,
    modified_at        timestamp,
    provider_name      varchar(28)             not null        unique,
    description        varchar(255),
    priority           integer                 not null,
    active             boolean   default true,
    default_multiplier numeric   default 1.0   not null
);


create index rate_providers_code_uidx
    on rate_providers (provider_code);

INSERT INTO rate_providers (provider_code, provider_name, description, priority, active)
VALUES ('fix', 'fixer', 'fixer.io', 1, true);
