create table match
(
    match_id          CHAR(16) FOR BIT DATA not null,
    code_length       integer      not null,
    codes_to_generate integer      not null,
    created           timestamp    not null,
    criterion         integer      not null,
    ending            timestamp    not null,
    pool              varchar(255) not null,
    originator_id     CHAR(16) FOR BIT DATA not null,
    primary key (match_id)
);

create table user_match_participation
(
    user_id  CHAR(16) FOR BIT DATA not null,
    match_id CHAR(16) FOR BIT DATA not null
);

create table user_profile
(
    user_id      CHAR(16) FOR BIT DATA not null,
    created      timestamp    not null,
    display_name varchar(255) not null,
    inactive     boolean      not null,
    oauth_key    varchar(255) not null,
    primary key (user_id)
);
create
index IDXhn44sdbv47nhy15uawe1q3ngy on match (code_length, pool)
create
index IDXa61daknaxmyj20x9kbs0pas27 on match (codes_to_generate)
create
index IDXh6uulg9i7qmpl9u20pobq0cns on match (ending)

alter table user_profile
    add constraint UK_j35xlx80xoi2sb176qdrtoy69 unique (display_name);

alter table user_profile
    add constraint UK_6f815wi5o4jq8p1q1w63o4mhd unique (oauth_key);

alter table match
    add constraint FK75k7qj50d7mtu2vyrhh0tgdus
        foreign key (originator_id)
            references user_profile;

alter table user_match_participation
    add constraint FKa4rma6bwvivsw0vps1h9gpica
        foreign key (match_id)
            references match;

alter table user_match_participation
    add constraint FKiwy0bph3h597792xvl9royhgp
        foreign key (user_id)
            references user_profile;
