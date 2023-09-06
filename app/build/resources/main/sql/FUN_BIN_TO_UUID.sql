-- 데이터베이스에 저장된 BINARY(16) 데이터를 대시가 포함된 UUID 로 변경합니다.
CREATE FUNCTION BIN_TO_UUID(bin BINARY(16))
    RETURNS BINARY(36)
    DETERMINISTIC
BEGIN
    DECLARE hex CHAR(32);
    SET hex = HEX(bin);
    RETURN LOWER(CONCAT(
            LEFT(hex, 8),
            '-',
            MID(hex, 9, 4),
            '-',
            SUBSTR(hex, 13, 4),
            '-',
            SUBSTR(hex, 17, 4),
            '-',
            RIGHT(hex, 12)
        ));
END;