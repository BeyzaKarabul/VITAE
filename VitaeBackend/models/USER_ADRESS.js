/* jshint indent: 2 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define('USER_ADRESS', {
    user_id: {
      type: DataTypes.STRING,
      allowNull: false,
      references: {
        model: 'USERS',
        key: 'user_id'
      }
    },
    country_id: {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      references: {
        model: 'COUNTRIES',
        key: 'country_id'
      }
    },
    state_id: {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      references: {
        model: 'STATES',
        key: 'state_id'
      }
    },
    province_id: {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      references: {
        model: 'PROVINCES',
        key: 'province_id'
      }
    },
    city_id: {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      references: {
        model: 'CITIES',
        key: 'city_id'
      }
    },
    distict_id: {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      references: {
        model: 'DISTRICT',
        key: 'distict_id'
      }
    },
    street_adress: {
      type: DataTypes.STRING,
      allowNull: false
    }
  }, {
    tableName: 'USER_ADRESS'
  });
};