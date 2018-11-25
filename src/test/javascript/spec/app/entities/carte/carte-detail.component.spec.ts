/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StBarnabeTestModule } from '../../../test.module';
import { CarteDetailComponent } from 'app/entities/carte/carte-detail.component';
import { Carte } from 'app/shared/model/carte.model';

describe('Component Tests', () => {
    describe('Carte Management Detail Component', () => {
        let comp: CarteDetailComponent;
        let fixture: ComponentFixture<CarteDetailComponent>;
        const route = ({ data: of({ carte: new Carte(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [CarteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CarteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CarteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.carte).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
