'use strict';

/**
 * Translation service.
 */
angular.module('docs').factory('Translation', function($http, $q, Restangular) {
  var service = {};

  /**
   * Detect language of text.
   * Returns 'en' for English or 'zh' for Chinese.
   */
  service.detectLanguage = function(text) {
    // Simple language detection - check if there are Chinese characters
    const chineseRegex = /[\u4e00-\u9fff]/;
    return chineseRegex.test(text) ? 'zh' : 'en';
  };

  /**
   * Translate text using SiliconFlow API.
   * If text is in Chinese, translate to English.
   * If text is in English, translate to Chinese.
   */
  service.translateText = function(text) {
    var deferred = $q.defer();
    
    if (!text) {
      deferred.reject('No text to translate');
      return deferred.promise;
    }

    var sourceLanguage = service.detectLanguage(text);
    
    // Use $http instead of Restangular to have more control over headers
    $http({
      method: 'POST',
      url: '../api/translation',
      headers: {
        'Content-Type': 'application/json'
      },
      data: JSON.stringify({
        text: text,
        sourceLanguage: sourceLanguage
      })
    }).then(function(response) {
      if (response && response.data && response.data.translation) {
        deferred.resolve(response.data.translation);
      } else {
        deferred.reject('Invalid response from translation service');
      }
    }, function(error) {
      deferred.reject('Error calling translation service: ' + JSON.stringify(error));
    });

    return deferred.promise;
  };

  return service;
}); 

// AI-Generation: by Cursor
// prompt： Translating a document to other languages after opening it.