<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "position".
 *
 * @property integer $id
 * @property integer $truck_id
 * @property double $latitude
 * @property double $longitude
 * @property string $geo_gps
 * @property string $nome
 * @property string $cpf
 * @property string $rg
 * @property string $contato
 * @property string $antt
 */
class Position extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'position';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['truck_id', 'latitude', 'longitude', 'nome'], 'required'],
            [['truck_id'], 'integer'],
            [['latitude', 'longitude'], 'number'],
            [['geo_gps','cpf','rg','contato','antt'], 'safe'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'truck_id' => 'Truck ID',
            'latitude' => 'Latitude',
            'longitude' => 'Longitude',
            'geo_gps' => 'Geo Gps',
        ];
    }
}
