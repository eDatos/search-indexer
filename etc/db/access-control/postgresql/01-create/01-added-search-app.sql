------------------------------------------------------------------------------------
-- BUSCAISTAC-27 - Crear aplicativo web de gestión que sustituya al idxmanager
------------------------------------------------------------------------------------

-- TB_APPS SEARCH
Insert into TB_APPS (ID, CODE, UUID, TITLE, DESCRIPTION, VERSION, CREATED_BY, CREATED_DATE, CREATED_DATE_TZ, LAST_UPDATED_BY, LAST_UPDATED, LAST_UPDATED_TZ) values 
(GET_NEXT_SEQUENCE_VALUE('APPS'),'GESTOR_BUSCADOR','64ccd0f3-51a1-458f-bf95-7cf00835ee5b','Gestor del buscador', 'Aplicativo encargado de gestionar la configuración e índices del buscador', 1, 'search_app', current_timestamp, 'Europe/London', 'access_control_app', current_timestamp, 'Europe/London');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'APPS';
