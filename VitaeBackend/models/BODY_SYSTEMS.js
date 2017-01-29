/* jshint indent: 2 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define('BODY_SYSTEMS', {
    body_system_id: {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      primaryKey: true,
      autoIncrement: true
    },
    system_name: {
      type: DataTypes.STRING,
      allowNull: false
    }
  }, {
    tableName: 'BODY_SYSTEMS'
  });
};
