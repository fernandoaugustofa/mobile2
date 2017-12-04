<?php
namespace app\controllers;

use yii\rest\ActiveController;

class PositionController extends ActiveController
{
    private $format = 'json';
    public $modelClass = 'app\models\Position';
}
