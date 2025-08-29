# Assignment1-cmart
Build a console-based Java application that interacts with a SQL database to manage Energy Trade records using CRUD operations.

Database Setup (SQL)

Create a database EnergyTradingDB with the following table:

CREATE TABLE Trades (

TradeID INT PRIMARY KEY IDENTITY(1,1),

TradeDate DATE NOT NULL,

Counterparty VARCHAR(100) NOT NULL,

Commodity VARCHAR(50) NOT NULL, -- e.g., Power, Gas, Oil

Volume DECIMAL(10,2) NOT NULL, -- e.g., MWh, MMBtu

Price DECIMAL(10,2) NOT NULL, -- Price per unit

TradeType VARCHAR(10) CHECK (TradeType IN ('BUY','SELL'))

);



Java Application Requirements

· Connect the Java program to the SQL database using JDBC.

· Implement a menu-driven console application with the following options:

Menu Options:

1. Add a Trade → Insert a new trade record (Buy/Sell).

2. View All Trades → Fetch and display all trades.

3. Update Trade → Update trade details (e.g., Price, Volume).

4. Delete Trade → Remove a trade by TradeID.

5. Search Trades by Counterparty / Commodity → Fetch trades by filter.

6. Exit → Exit the program.

---

Deliverables

Upload the following in GitHub and share the GitHub URL in the Activity Tracker Excel sheet (Shared Folder):

· SQL Script to create the database & table.

· Java Source Code implementing CRUD operations with JDBC.

· Console app - Screenshots and Output Logs showing test runs of the Menu options.

