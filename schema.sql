CREATE TABLE IF NOT EXISTS Product_Mi_Inspection (
    id INT AUTO_INCREMENT PRIMARY KEY,
    template_name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    worker_name VARCHAR(100) NOT NULL,
    inspection_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    raw_data JSON NOT NULL,
    status VARCHAR(30) NOT NULL
);
