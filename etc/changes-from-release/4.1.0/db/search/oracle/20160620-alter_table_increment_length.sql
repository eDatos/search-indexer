-- --------------------------------------------------------------------------------------------------
-- BUSCAISTAC-51 - Dar un error controlado cuando el tamaño de los campos supera el límite
-- --------------------------------------------------------------------------------------------------
ALTER TABLE TB_RECOMMENDED_LINKS MODIFY URL VARCHAR2(4000 CHAR);
ALTER TABLE TB_RECOMMENDED_LINKS MODIFY DESCRIPTION VARCHAR2(4000 CHAR);

COMMIT;