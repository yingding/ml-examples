import { TestBed } from '@angular/core/testing';

import { SparkInfoService } from './spark-info.service';

describe('SparkInfoService', () => {
  let service: SparkInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SparkInfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
