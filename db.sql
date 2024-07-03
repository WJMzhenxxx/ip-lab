create table ip_original_fail_data
(
    id         bigserial
        constraint ip_original_fail_data_pk
            primary key,
    date       timestamp               not null,
    ip         inet                    not null,
    login_name text                    not null,
    tty        text                    not null,
    create_at  timestamp default now() not null,
    update_at  timestamp
);

alter table ip_original_fail_data
    owner to postgres;

create table ip_fail_summarize
(
    id        bigserial
        constraint ip_fail_summarize_pk
            primary key,
    ip        inet                    not null,
    count     bigint    default 1     not null,
    create_at timestamp default now() not null,
    update_at timestamp
);

alter table ip_fail_summarize
    owner to postgres;

create unique index ip_fail_summarize_ip_uindex
    on ip_fail_summarize (ip);

create materialized view ip_fail_snapshot as
SELECT ip,
       count(*)       AS count,
       max(create_at) AS end_date,
       min(create_at) AS start_date
FROM ip_original_fail_data
GROUP BY ip;

alter materialized view ip_fail_snapshot owner to postgres;

create function add_ip_summarize_func() returns trigger
    language plpgsql
as
$$
DECLARE
    nip inet;
    lock_id bigint;
begin
    nip := NEW.ip;
--     lock table ip_fail_summarize;
    lock_id = (nip - '0.0.0.0'::inet)::bigint;
    perform (select pg_advisory_xact_lock(lock_id));
    insert into ip_fail_summarize(ip)
    values (nip)
    on conflict (ip) do update set count=(select count from ip_fail_summarize where ip = nip) + 1,
                                   update_at=now();
    perform (select pg_advisory_unlock(lock_id));
    return null;

end;

$$;

alter function add_ip_summarize_func() owner to postgres;

create trigger ip_summarize
    after insert
    on ip_original_fail_data
    for each row
execute procedure add_ip_summarize_func();

