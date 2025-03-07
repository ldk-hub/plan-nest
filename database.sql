-- postgresql 100만건 더미 쿼리
INSERT INTO boards (boards_id, title, content, create_date)
SELECT
    nextval('board_id_seq'),
    CONCAT('Title', LPAD(n::TEXT, 7, '0')) AS title,
    CONCAT('Content', LPAD(n::TEXT, 7, '0')) AS content,
    NOW() - INTERVAL '1 day' * (random() * 3650) - INTERVAL '1 second' * (random() * 86400) AS create_date
FROM generate_series(1, 1000000) AS n;

