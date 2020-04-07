insert into settings(id, name, value)
values (nextval('hibernate_sequence'), 'MIN_LOAN_SUM', '100000'),
       (nextval('hibernate_sequence'), 'MAX_LOAN_SUM', '5000000'),
       (nextval('hibernate_sequence'), 'MIN_LOAN_PERIOD', '12'),
       (nextval('hibernate_sequence'), 'MAX_LOAN_PERIOD', '60'),
       (nextval('hibernate_sequence'), 'LOAN_RATE', '18.7');