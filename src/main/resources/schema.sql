-- Drop existing tables (in correct order due to potential dependencies)
DROP TABLE IF EXISTS certificate CASCADE;
DROP TABLE IF EXISTS placement CASCADE;
DROP TABLE IF EXISTS student CASCADE;
DROP TABLE IF EXISTS college CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS admin CASCADE;

-- Recreate tables with proper SERIAL (auto-increment) columns

-- Admin table
CREATE TABLE admin (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Student table
CREATE TABLE student (
    sid SERIAL PRIMARY KEY,
    name VARCHAR(255),
    phone_no BIGINT,
    college_name VARCHAR(255)
);

-- College table
CREATE TABLE college (
    id SERIAL PRIMARY KEY,
    college_name VARCHAR(255),
    location VARCHAR(255)
);

-- Placement table
CREATE TABLE placement (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    qualification VARCHAR(255),
    year INTEGER
);

-- Certificate table
CREATE TABLE certificate (
    id SERIAL PRIMARY KEY,
    year INTEGER,
    college VARCHAR(255),
    qualification VARCHAR(255)
);

-- Users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    designation VARCHAR(255)
);

-- Insert sample data
INSERT INTO admin (name, password) VALUES 
    ('John Admin', 'admin123'),
    ('Sarah Manager', 'manager456'),
    ('Mike Super', 'super789');

INSERT INTO student (name, phone_no, college_name) VALUES 
    ('Alice Johnson', 9876543210, 'MIT'),
    ('Bob Smith', 8765432109, 'Stanford'),
    ('Carol White', 7654321098, 'Harvard');

INSERT INTO college (college_name, location) VALUES 
    ('MIT', 'Cambridge, MA'),
    ('Stanford', 'Palo Alto, CA'),
    ('Harvard', 'Cambridge, MA');

INSERT INTO placement (name, qualification, year) VALUES 
    ('Google', 'B.Tech CS', 2024),
    ('Microsoft', 'B.Tech IT', 2024),
    ('Amazon', 'B.Tech CS', 2023);

INSERT INTO certificate (year, college, qualification) VALUES 
    (2024, 'MIT', 'Machine Learning Nanodegree'),
    (2024, 'Stanford', 'Cloud Architecture Certification'),
    (2023, 'Harvard', 'Cybersecurity Professional Certificate');

INSERT INTO users (name, email, designation) VALUES 
    ('Admin User', 'admin@example.com', 'ADMIN'),
    ('Student User', 'student@example.com', 'STUDENT'),
    ('Faculty User', 'faculty@example.com', 'FACULTY');
