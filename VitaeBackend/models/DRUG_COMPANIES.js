/* jshint indent: 2 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define('DRUG_COMPANIES', {
    company_id: {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      primaryKey: true,
      autoIncrement: true
    },
    company_name: {
      type: DataTypes.STRING,
      allowNull: false
    }
  }, {
    tableName: 'DRUG_COMPANIES'
  });
};
