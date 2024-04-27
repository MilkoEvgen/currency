create table conversion_rates
(
    id               bigserial primary key,
    created_at       timestamp default now() not null,
    modified_at      timestamp,
    source_code      varchar(3)              not null references currencies (code),
    destination_code varchar(3)              not null references currencies (code),
    rate_begin_time  timestamp default now() not null,
    rate_end_time    timestamp               not null,
    rate             numeric                 not null,
    provider_code    varchar(3) references rate_providers,
    multiplier       numeric                 not null,
    system_rate      numeric                 not null
);