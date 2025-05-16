'use strict';

/**
 * File translate controller.
 */
angular.module('docs').controller('FileTranslateCtrl', function ($scope, $uibModalInstance, original, translated, sourceLanguage) {
  $scope.original = original;
  $scope.translated = translated;
  $scope.sourceLanguage = sourceLanguage;
  $scope.targetLanguage = sourceLanguage === 'zh' ? 'en' : 'zh';
  
  // Format language names for display
  $scope.getLanguageName = function(code) {
    switch(code) {
      case 'zh': return 'Chinese';
      case 'en': return 'English';
      default: return code;
    }
  };
  
  $scope.close = function() {
    $uibModalInstance.dismiss();
  };
  
  $scope.downloadTranslated = function() {
    // Create a blob and download it
    var blob = new Blob([translated], {type: 'text/plain;charset=utf-8'});
    var link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'translated_' + (sourceLanguage === 'zh' ? 'english' : 'chinese') + '.txt';
    link.click();
  };
}); 

// AI-Generation: by Cursor
// prompt： Translating a document to other languages after opening it.