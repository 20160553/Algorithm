SELECT COUNT(*) AS FISH_COUNT
FROM FISH_INFO
LEFT JOIN FISH_NAME_INFO on FISH_INFO.FISH_TYPE = FISH_NAME_INFO.FISH_TYPE
WHERE FISH_NAME_INFO.FISH_NAME = 'BASS'
OR FISH_NAME_INFO.FISH_NAME = 'SNAPPER';