truncate records, user_role, users;

ALTER SEQUENCE users_id_seq RESTART WITH 10;
ALTER SEQUENCE records_id_seq RESTART WITH 20;


insert into users (id, active, password, username)
values (1, true, '$2y$10$UH1CtGtELemMTFgqKKaCv.sw8CKeEcE5cL/g/igk3IKthix10xQGa', 'admin'),
       (2, true, '$2y$10$32oAq5NgttIuRJjUcaVnReVIwNPXKMeLFB25gTbaSwozFzsEiH8DO', 'user');

insert into user_role (user_id, roles)
values (1, 'ADMIN'),
       (1, 'USER'),
       (2, 'USER');

insert into records(id, date, tag, text, user_id, filename)
values (1, '2021-01-01', 'my-tag-1', 'my-text-1', 1, NULL),
       (2, '2021-02-23', 'my-tag-2', 'my-text-2', 1, NULL),
       (3, '2021-05-20', 'my-tag-1', 'my-text-3', 1, NULL),
       (4, '2021-05-20', 'my-tag-3', 'my-text-4', 2, NULL);