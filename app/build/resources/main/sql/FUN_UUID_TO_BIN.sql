-- UUID 를 BINARY(16) 으로 변환합니다.
create function UUID_TO_BIN(uuid BINARY(36))
    RETURNS BINARY(16)
    DETERMINISTIC
BEGIN
    RETURN UNHEX(REPLACE(uuid, '-', ''));
END;