const { Model, DataTypes } = require('sequelize');
const dbManager = require('../db-manager');

const sequelize = dbManager.getConnection();

class Customer extends Model {}
Customer.init({
  id: {type: DataTypes.INTEGER, primaryKey: true},
  fullName: DataTypes.STRING,
  contactEmail: DataTypes.STRING,
  primaryPhone: DataTypes.STRING,
  location: DataTypes.STRING,
}, { sequelize, modelName: 'customer' });

module.exports.customerRepo = Customer;