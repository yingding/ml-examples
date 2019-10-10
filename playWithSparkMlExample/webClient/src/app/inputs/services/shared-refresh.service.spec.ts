import { TestBed } from '@angular/core/testing';

import { SharedRefreshService } from './shared-refresh.service';

describe('SharedRefreshService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SharedRefreshService = TestBed.get(SharedRefreshService);
    expect(service).toBeTruthy();
  });
});
